
../gradlew build

docker build -f src/main/docker/Dockerfile.jvm -t docker.dankito.net/dankito/bank-finder-api:1.0.0-beta1 .

docker push docker.dankito.net/dankito/bank-finder-api:1.0.0-beta1