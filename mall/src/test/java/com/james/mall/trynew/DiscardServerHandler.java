package com.james.mall.trynew;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)
    Logger logger= LoggerFactory.getLogger(DiscardServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
//        ctx.write(msg); // (1)
//        ctx.flush(); // (2)
        logger.warn("service channelRead");
        ByteBuf in = (ByteBuf) msg;
        try {
            StringBuilder sb=new StringBuilder();
            while (in.isReadable()) {
                 sb.append((char) in.readByte());
            }
            System.out.println(sb.toString());
        } finally {
            ReferenceCountUtil.release(msg);
        }
        ctx.close();//for test
//
//        final ByteBuf time = ctx.alloc().buffer(4);
//        time.writeBytes("service reply".getBytes());
//        ctx.writeAndFlush(time);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

//    @Override
//    public void channelActive(final ChannelHandlerContext ctx) { // (1)
//        final ByteBuf time = ctx.alloc().buffer(4); // (2)
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
////        time.writeBytes("hello world".getBytes());
//        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
////                ctx.close();
//            }
//        }); // (4)
//    }


}