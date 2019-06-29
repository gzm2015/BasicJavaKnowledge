package netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.net.InetSocketAddress;
import java.util.List;

public class UdpBroadcast {

    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new UdpSendHandler(new InetSocketAddress("255.255.255.255",1024)));
                        }
                    });
            ChannelFuture future = bootstrap.bind(0).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (future.isSuccess()) {
                        System.out.println("连接成功 准备发送udp数据");
                    } else {
                        Throwable cause = future.cause();
                        cause.printStackTrace();
                    }
                }
            });
            Channel channel =  future.channel();
            channel.writeAndFlush("发送udp数据测试");
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                nioEventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}






/**
 * 最后几个handler 将数据加密为udp格式
 */
class UdpSendHandler extends MessageToMessageEncoder<String> {

    private InetSocketAddress inetSocketAddress;

    public UdpSendHandler(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String data, List<Object> list) throws Exception {
        System.out.println("发送数据:   "+data);
        ByteBuf buf = channelHandlerContext.alloc().buffer();
        buf.writeBytes(data.getBytes());
        list.add(new DatagramPacket(buf, inetSocketAddress));
    }
}