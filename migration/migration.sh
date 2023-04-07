#!/bin/bash

until pg_isready
do
  echo 'waiting for database to be up'
  sleep 2
done

run_sql() {
  psql -f $1

  exist_status=$?
  if [ ${exist_status} -ne 0 ]; then
    echo "run failed for $1"
    exit 1
  fi
  echo "seed for file: $1 done."
}

FILES_FOLDER="`pwd`/migration"
for FILE in $(ls ${FILES_FOLDER})
do
  echo ""
	echo "processing ...${FILES_FOLDER}/${FILE}:"
  run_sql "${FILES_FOLDER}/${FILE}"
  sleep 1
done
