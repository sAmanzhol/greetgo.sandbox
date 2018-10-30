#!/bin/sh

export PG_ADMIN_USERID=postgres
export PG_ADMIN_PASSWORD=111
export PG_ADMIN_URL=jdbc:postgresql://localhost/yyelgondy_sandbox

gradle dropCreateOperDb dropCreateCiaDb
