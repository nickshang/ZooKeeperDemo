package com.shang.zk;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * ZK连接、创建文件、删除文件事件监听
 * Created by Think on 2017/7/5.
 */
public class ZkDemo {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        // 创建一个与服务器的连接
       ZooKeeper zk = new ZooKeeper("192.168.0.212:2181",
               3000, watchedEvent -> { System.out.println("已经触发事件:" +  watchedEvent.getType() + "事件!"); } );

       // 创建一个目录节点
       zk.create("/testRootPath","testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

       // 创建一个子目录及节点
        zk.create("/testRootPath/testChildPathOne",
            "testChildDataOne".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT
        );
        System.out.println(new String(zk.getData("/testRootPath",false,null)));

        // 取出子目录节点列表
        System.out.println(zk.getChildren("/testRootPath",true));

        // 修改值目录节点数据
        zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1);
        System.out.println("目录节点状态:[" + zk.exists("/testRootPath",true) + "]");

        // 创建另外一个子目录节点
        zk.create("/testRootPath/testChildPathTwo","testChildPathTwo".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

        // 删除值目录节点
        zk.delete("/testRootPath/testChildPathTwo",-1);
        zk.delete("/testRootPath/testChildPathOne",-1);

        // 删除父目录节点
        zk.delete("/testRootPath",-1);

        zk.close();

    }
}
