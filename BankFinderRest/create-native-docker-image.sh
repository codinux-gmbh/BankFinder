
../gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

docker build -f src/main/docker/Dockerfile.native-micro -t docker.dankito.net/dankito/bank-finder-api:1.0.0-beta1 .

docker push docker.dankito.net/dankito/bank-finder-api:1.0.0-beta1