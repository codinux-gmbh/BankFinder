
../gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true -x test

docker build -f src/main/docker/Dockerfile.native-micro -t docker.dankito.net/dankito/bank-finder-api .

docker push docker.dankito.net/dankito/bank-finder-api