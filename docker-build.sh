
DOCKER_REPO=$1

if [ -z $DOCKER_REPO ]; then
  echo "You must input docker hub repository name."
  exit 1;
fi

./mvnw clean package install;

ROOT_DIR=`pwd`

cd $ROOT_DIR/order-service
./mvnw dockerfile:build;
cd $ROOT_DIR/shipping-service
./mvnw dockerfile:build;
cd $ROOT_DIR/payment-service
./mvnw dockerfile:build;

docker push $DOCKER_REPO/payment-service:latest
docker push $DOCKER_REPO/order-service:latest
docker push $DOCKER_REPO/shipping-service:latest

