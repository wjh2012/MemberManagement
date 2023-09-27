#!/bin/sh
if [ -n "$(docker ps -aq)" ]; then
  docker stop $(docker ps -aq)
  docker rm $(docker ps -a -q)
fi

if [ -n "$(docker images -q)" ]; then
  docker rmi $(docker images -q)
fi

docker volume prune -f

docker-compose up -d