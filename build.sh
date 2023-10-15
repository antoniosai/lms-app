#!/bin/sh
mvn clean install && docker build -t app/lms:latest . && docker-compose up -d --force-recreate --remove-orphans