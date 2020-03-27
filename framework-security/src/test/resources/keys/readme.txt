# 进入OpenSSL程序，按步骤生成RSA公私钥
$ openssl
OpenSSL> genrsa -out rsa_private_key.pem 1024
OpenSSL> pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt -out rsa_private_key_pkcs8.pem
OpenSSL> rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
OpenSSL> exit