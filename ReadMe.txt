#######################
#			#
#            程序执行方式              #
#                                              #
#######################

############################
法一：
使用cmd，进入src目录
>>cd mypkg                #进入mypkg包的文件夹
>>javac *.java              #编译所有java文件
>>cd ..                         #回到src目录
>>SET CLASSPATH=.   #设置环境变量，否则找不到MAIN类
>>java mypkg.MAIN   #执行MAIN类的main入口函数
Serial Qucik Sort: ...      #打印运行时间
Serial Merge Sort: ...
Serial Enumeration Sort: ...
Parallel Quick Sort: ...
Parallel Merge Sort: ...
Parallel Enumeration Sort: ...
此种方式下文件输出到src目录，运行前请确保环境变量设置正确且random.txt在src\mypkg目录下
############################

############################
法二：
打开IntelliJ IDEA，点击运行即可
此种方式文件输出到src上层目录
############################

其他参数更改详见报告
