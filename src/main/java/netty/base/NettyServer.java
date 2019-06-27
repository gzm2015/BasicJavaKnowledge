package netty.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;


/**
 * netty 服务器
 */
public class NettyServer {

    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        serverBootstrap
                .group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(8081)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });

        try {
            ChannelFuture future = serverBootstrap.bind().sync();
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
        }finally {
            try {
                nioEventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Server Netty rocks!",
                CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf in) throws Exception {
        System.out.println(
                "Server received: " + in.toString(CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}