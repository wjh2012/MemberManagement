#!/bin/sh
docker stop $(docker ps -aq)
docker rm $(docker ps -a -q)
docker rmi $(docker images -q)
docker compose up -d