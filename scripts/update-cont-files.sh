BASEDIR=$(dirname "$0")
docker exec rmq-solver-cont rm -rf /root/src
docker cp $BASEDIR/../src rmq-solver-cont:/root/src/
