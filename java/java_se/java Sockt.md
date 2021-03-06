#java Socket 编程

1.网络基础知识

2.InetAddress类

3.URL

4.TCP编程

5.UDP编程

![](http://i.imgur.com/6c5z5br.png)

两台计算机通过网络进行通信

必备条件: ip ,协议，端口

![](http://i.imgur.com/UvSiJd6.png)


      TCP/IP协议

![](http://i.imgur.com/obOibgL.png)

TCP/IP 模型

![](http://i.imgur.com/A9QeiSt.png)

IP地址:

![](http://i.imgur.com/CouNmkX.png)

端口:

![](http://i.imgur.com/9ujKLsG.png)

![](http://i.imgur.com/IdKPwFg.png)

java 中对网络的支持

![](http://i.imgur.com/yOBCTOd.png)

# InetAddress

  `
 `
package com.imooc;

import java.net.InetAddress;

import java.net.UnknownHostException;

import java.util.Arrays;


/**

 * InetAdress类


 * @author 彭文浩
 * 
 *
 */

public class Test01 {

	public static void main(String[] args) throws UnknownHostException {

		InetAddress address = InetAddress.getLocalHost();
		System.out.println("计算机名:" + address.getHostName());
		System.out.println("计算机IP地址:" + address.getHostAddress());
		byte[] addr = address.getAddress();
		System.out.println("字节数组的ip地址:" + Arrays.toString(addr));
		System.out.println(address); // 直接获取计算机名和ip地址

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		// 根据计算机名获取InetAddress
		InetAddress address2 = InetAddress.getByName("PengWenHao");
		System.out.println("计算机名:" + address2.getHostName());
		System.out.println("计算机IP地址:" + address2.getHostAddress());

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		// 根据计算的IP地址获取InetAddress
		// InetAddress address3 = InetAddress.getLocalHost();
		// 或者
		InetAddress address3 = InetAddress.getByName("192.168.80.1");
		System.out.println("计算机名:" + address3.getHostName());
		System.out.println("计算机IP地址:" + address3.getHostAddress());

	}
}
`
`

运行结果:

 ![](http://i.imgur.com/wn1MTEw.png)






##

#URL 

![](http://i.imgur.com/GDDWrX5.png)

  ```
package com.imooc;


import java.net.MalformedURLException;

import java.net.URL;


/**

 * URL常用的 方法


 * @author 彭文浩

 *
 */


public class Test02 {

	public static void main(String[] args) {

		// 创建一个URL实例
		try {

			URL imooc = new URL("http://www.imooc.com");
			// ? 后面表示参数，#后面表示描点
			URL url = new URL(imooc, "/index.html?username=tom#test");
			System.out.println("协议:" + url.getProtocol());
			System.out.println("主机:" + url.getHost());
			// 如果没有指定端口号,则使用默认的端口号,此时getPort() 返回的是-1
			System.out.println("端口:" + url.getPort());
			System.out.println("文件路径:" + url.getPath());
            System.out.println("相对路径:"+url.getRef());
            System.out.println("查询字符串:"+url.getQuery());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


```

运行结果:

![](http://i.imgur.com/PcB7bMo.png)


##

![](http://i.imgur.com/pVJckFS.png)


```

package com.imooc;

import java.io.BufferedReader;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.URL;


/**

 *  使用url读取页面上的类容

 * @author 彭文浩

 *
 */

public class Test03 {
	
	public static void main(String[] args) {
		
		try {
			URL url = new URL("http://www.baidu.com");
		   // 通过URL的openStream 方法获取URL对象所表示的资源字节输入流
			InputStream is =  url.openStream();
			// 将字节输入流转换为字符输入流
			InputStreamReader isr = new InputStreamReader(is,"utf-8");
		    // 为字符输入流添加缓冲
			BufferedReader br = new BufferedReader(isr);
			String data = br.readLine(); // 读取数据
			while(data!=null){
				System.out.println(data); // 输出数据
				data = br.readLine(); 
			}
			br.close();
			isr.close();
			is.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}

}

```
运行结果：

![](http://i.imgur.com/L2FMcVL.png)


##


# Socket通信

![](http://i.imgur.com/jnXKgkF.png)


![](http://i.imgur.com/PSpe90a.png)

![](http://i.imgur.com/UbM6Mh0.png)


##

客户端和服务端

![](http://i.imgur.com/hXIIxS5.png)

![](http://i.imgur.com/VUyNxcW.png)

##

demo:

 > 客户端:

package com.imooc;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.io.PrintWriter;

import java.net.Socket;

import java.net.UnknownHostException;


/**

 * 客户端
 

 * @author 彭文浩

 */

 
public class Client {

	public static void main(String[] args) {
		try {
			//1.创建客户端Socket，指定服务器地址和端口
			Socket socket=new Socket("localhost", 8888);
			//2.获取输出流，向服务器端发送信息
			OutputStream os=socket.getOutputStream();//字节输出流
			PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
			pw.write("用户名：alice;密码：789");
			pw.flush();
			socket.shutdownOutput();//关闭输出流
			//3.获取输入流，并读取服务器端的响应信息
			InputStream is=socket.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String info=null;
			while((info=br.readLine())!=null){
				System.out.println("我是客户端，服务器说："+info);
			}
			//4.关闭资源
			br.close();
			is.close();
			pw.close();
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

  
> 服务端

package com.imooc;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.io.PrintWriter;

import java.net.ServerSocket;

import java.net.Socket;


/**

 * 基于TCP协议的Socket通信,实现用户登录 服务端


 * @author 彭文浩
 
 */

public class Server {

	public static void main(String[] args) {

		try {
			// 1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
			ServerSocket serverSocket = new ServerSocket(8888);
			// 2. 调用accept() 方法开始监听,等待客户端的连接
			System.out.println("***服务器即将启动,等待客户端的连接***");
			Socket socket = serverSocket.accept();
			
			// 3. 获取输入 流,并读取客户信息
			InputStream is = socket.getInputStream(); // 字节输入流
			InputStreamReader isr = new InputStreamReader(is); // 将字节流转换为字符流
			BufferedReader br = new BufferedReader(isr); // 为输入流添加缓冲
			String info = null;
			while ((info = br.readLine())!= null) {   //  循环读取客户端信息
               System.out.println("我是服务,客户端说:"+info);
			}
			socket.shutdownInput(); // 关闭输入流
			
			// 4.获取输出流,响应客户单请求
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os); // 包装成打印流
			pw.write("欢迎您!");
		    pw.flush();  // 调用flush()方法将缓冲输出
		    
		    
		    
			// 5.关闭资源
		    pw.close();
		    os.close();
			br.close();
			isr.close();
			is.close();
			socket.close();
			serverSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}


 此时要先运行服务端后运行客户端，结果如图:

![](http://i.imgur.com/VydSBBW.png)

![](http://i.imgur.com/pr4TQF9.png)


##

#多线程服务器

##

![](http://i.imgur.com/mFWsAf8.png)

##

code：

package com.imooc;


import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.io.PrintWriter;

import java.net.Socket;


/**

 * 服务器线程处理类
 
 
 * @author 彭文浩
 * 
 
 */

public class ServerThread extends Thread {

	// 和本地线程相关的Socket
	Socket socket = null;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	// 线程执行操作,响应客户端的请求
	public void run() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStream os = null;
		PrintWriter pw = null;
		try {
			// 获取输入流，并读取客户端信息
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String info = null;
			while ((info = br.readLine()) != null) {// 循环读取客户端的信息
				System.out.println("我是服务器，客户端说：" + info);
			}
			socket.shutdownInput();// 关闭输入流
			// 获取输出流，响应客户端的请求
			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			pw.write("欢迎您！");
			pw.flush();// 调用flush()方法将缓冲输出
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (pw != null)
					pw.close();
				if (os != null)
					os.close();
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (is != null)
					is.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

 
  

服务端:

package com.imooc;


import java.io.IOException;

import java.net.InetAddress;

import java.net.ServerSocket;

import java.net.Socket;


public class Server2 {

	public static void main(String[] args) {

		try {
			// 1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
			ServerSocket serverSocket = new ServerSocket(8888);
			Socket socket = null;
			// 记录客户端的数量
			int count = 0;
			System.out.println("***服务器即将启动，等待客户端的连接***");
			// 循环监听等待客户端的连接
			while(true){
				//调用accept()方法开始监听，等待客户端的连接
				socket=serverSocket.accept();
				//创建一个新的线程
				ServerThread serverThread=new ServerThread(socket);
				//启动线程
				serverThread.start();
				
				count++;//统计客户端的数量
				System.out.println("客户端的数量："+count);
				InetAddress address=socket.getInetAddress();
				System.out.println("当前客户端的IP："+address.getHostAddress());
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

客户端代码同上,同样先运行服务端后运行客户端,结果如图:

![](http://i.imgur.com/eNmSPZc.png)

![](http://i.imgur.com/c3QKsXj.png)

##

#UDP编程

![](http://i.imgur.com/y5e9r9j.png)

![](http://i.imgur.com/erQa3RA.png)

![](http://i.imgur.com/3VRaMIA.png)

![](http://i.imgur.com/XQxFR6L.png)

![](http://i.imgur.com/qVQwBU2.png)


demo ：

客户端：

package com.imooc;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;


/**

 * 客户端
 

 * @author 彭文浩


 *
 */

public class UDPClient {

	public static void main(String[] args) {
		try {
			/**
			 * 向服务端发送数据
			 */

			// 1.定义服务器的地址,端口号,数据
			InetAddress address = InetAddress.getByName("localhost");
			int part = 8800;
			byte[] data = "用户名:admin;密码:123".getBytes();
			// 2. 创建数据报,包含发送的数据信息
			DatagramPacket packet = new DatagramPacket(data, data.length, address, part);
			// 3. 创建DatagramSocket 对象
			DatagramSocket socket = new DatagramSocket();
			// 4.向服务器端发送数据报
			socket.send(packet);

			/**
			 * 接受服务端响应的数据
			 */
			// 1.创建数据报，用于接受服务器响应的数据
			byte[] data2 = new byte[1024];
			DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
			// 2. 接受服务端响应的数据
			socket.receive(packet2);
			// 3.读取数据
			String reply = new String(data2, 0, packet2.getLength());
			System.out.println("我是客户端,服务端说:" + reply);
			// 4.关闭资源
			socket.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}


服务端:

package com.imooc;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;


/**

 * 服务器端，实现基于UDP的用户登录

 * @author 彭文浩

 */

public class UDPServer {

	public static void main(String[] args) {
             /**
              *  接受客户端发送的数据
              */
		 
		try {
			// 1. 创建服务端DatagramSocket,指定端口号
			DatagramSocket socket = new DatagramSocket(8800);
			// 2. 创建数据报,用于接受客户端发送的数据
			byte[] data = new byte[1024]; // 创建字节数组,指定接受数据包的大小
			DatagramPacket packet = new DatagramPacket(data, data.length);
			// 3. 接受客户端发送的数据
			System.out.println("***服务器端已经启动,等待客户端**");
			socket.receive(packet); // 此方法在接受数据报之前会一直阻塞
			// 4. 读取数据
			String info = new String(data, 0, packet.getLength());
			System.out.println("我是服务器,客户端说:" + info);

			/**
			 *  向客户端响应数据
			 */
			// 1.定义客户端的地址,端口号,数据
			InetAddress address = packet.getAddress();
			int part = packet.getPort();
			byte [] data2 = "欢迎您!".getBytes();
			// 2. 创建数据报,包含响应的数据信息
			DatagramPacket packet2 = new DatagramPacket(data2,data2.length,address,part);
			// 3. 响应客户端
			socket.send(packet2);
			
			// 4.关闭资源
			 socket.close();
			 
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

先启动服务端，后启动客户端

运行结果图:

![](http://i.imgur.com/gd0PsSr.png)

![](http://i.imgur.com/YLfdU7l.png)



##
Socket编程总结:

![](http://i.imgur.com/qAQdUvv.png)

![](http://i.imgur.com/OpX0kkZ.png)

![](http://i.imgur.com/JyjkXHz.png)

![](http://i.imgur.com/b6E2tRh.png)

![](http://i.imgur.com/b1pIY2X.png)

![](http://i.imgur.com/OsHBzJl.png)

![](http://i.imgur.com/dDqWsjW.png)
