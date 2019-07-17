
ROOT_DIR=`pwd`


cd $ROOT_DIR/order-service
./mvnw dockerfile:build;
cd $ROOT_DIR/shipping-service
./mvnw dockerfile:build;
cd $ROOT_DIR/payment-service
./mvnw dockerfile:build;

