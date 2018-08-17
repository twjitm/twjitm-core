/**本类主要是网络服务启动类，
 * 主要用来监控服务器的启动，关闭，注销等操作
 * 一般用于打包后，设置启动类Bootstrap的main类启动类。
 *
 *
 *                    | NettyGameBootstrapTcpService
 * Bootstrap--------->| NettyGameBootstrapHttpService
 *                    | NettyGameBootstrapUdpService
 *
 *
 *
 * **/