package netty.udp;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import java.util.List;

/**
 * 接受udp工具类
 */
public class UpdClient {

    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(nioEventLoopGroup)
                    .localAddress(8888)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new UdpHandler());
                            channel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                    System.out.println(s);
                                    //writeAndFlush 可以写到 outboundChannel里面
                                    channelHandlerContext.writeAndFlush(s);
                                }
                            });
                            channel.pipeline().addLast(new OutboundHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind().syncUninterruptibly();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture)
                        throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("Server bound");
                    } else {
                        System.err.println("Bind attempt failed");
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
            future.channel().closeFuture().sync();
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


//必须使用 MessageToMessageDecoder 并制定传入数据格式为 DatagramPacket
class UdpHandler extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf byteBuf = datagramPacket.content();
        //如果指定 toString 的编码格式打印的是butebuf对象
        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
        //添加到list中 传递到下一个handler中
        list.add("test ok");
    }
}


/**
 * 测试Outbound
 */
class OutboundHandler extends ChannelOutboundHandlerAdapter{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(msg);
        ctx.writeAndFlush(msg);
    }
}