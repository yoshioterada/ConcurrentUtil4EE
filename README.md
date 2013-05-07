ConcurrentUtil4EE
=================

concurrent utilities for Java EE 7

※1 本プログラムを動作させるために、AppServer-メールサーバの設定が必要
GlassFish v4.0 でGMailに接続する場合
asadmin コマンドで下記を実行
# asadmin create-javamail-resource
--mailhost "smtp.gmail.com"
--mailuser "foo@gmail.com"
--fromaddress "foo@gmail.com"
--property "mail.smtp.socketFactory.class=javax.net.ssl.SSLSoc ketFactory:mail.smtp.socketFactory.port=465:mail.smtp.port=465:mail.smtp.socketFactory.fallback=false:mail.smtp.auth=true:mail.smtp.password=[FOO's Password]" mail/MyMailSession


※2 本プログラムを動作させるために、AppServer の MQ の物理送信先の設定が必要
GlassFish v4.0 で MQ の物理宛先の設定方法
asadmin コマンドで下記を実行
# asadmin create-jms-resource
--restype javax.jms.Queue
--property Name=registQueue
jms/mailRegistQueue

※3 本プログラムを動作させるために、AppServer でJDBCリソースの設定が必要
GlassFish v4.0 で JDBC リソースの設定方法(Derbyを利用する場合)
asadmin コマンドで下記を実行
#asadmin create-jdbc-resource
--connectionpoolid DerbyPool
jdbc/sample


