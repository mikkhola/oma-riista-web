#!/usr/bin/env bash
set -e
cd "$(dirname "$0")" # Ensuring correct working directory

DATABASE="$1"
SOURCE_FILE=$(find ./shp/hta/ -not -path "*/\.*" -type f -name "*.shp")

if [ -z "${DATABASE}" ]; then
    echo "Usage: $0 <target-db>"
    exit 1
fi

if [ ! -f "${SOURCE_FILE}" ]; then
    echo "Source shape-file not found at ${SOURCE_FILE}"
    exit 2
else
    echo "Using source file: ${SOURCE_FILE}"
fi

psql -d "${DATABASE}" -c "DROP TABLE IF EXISTS import_hta;"

shp2pgsql -c -s 3067 -W UTF-8 -I -D "${SOURCE_FILE}" public.import_hta | psql "${DATABASE}"

psql -v ON_ERROR_STOP=1 -d "${DATABASE}" -f ./sql/hta.sql
