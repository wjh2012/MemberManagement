#!/bin/sh
docker compose up -d
docker ps -a
docker system prune -f