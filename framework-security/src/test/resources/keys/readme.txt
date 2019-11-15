#convert rsa private key to pkcs8 format so that java can read it
openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key_file  -nocrypt > pkcs8_key

Generate rsa keypair
$ openssl   进入OpenSSL程序
 OpenSSL> genrsa -out rsa_private_key.pem 1024   生成私钥
 OpenSSL> pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt     拷贝此命令的输出保存为私钥
 OpenSSL> rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem    生成公钥
 OpenSSL> exit  