#!/bin/bash

echo "Let's update the DB"

DB_DIR=/opt/db
LOCK=/var/lock/db-updater.lock

remove_lock() {
  rm -rf ${DB_DIR:?}/$name
  rm -rf "$LOCK"
  echo "Update finished..."
}

another_instance() {
  echo "The other instance is running, exit..."
  exit 1
}

mkdir $LOCK || another_instance
trap remove_lock EXIT

declare -a files=("lib.libavtor.sql"
                  "lib.libtranslator.sql"
                  "lib.libavtorname.sql"
                  "lib.libbook.sql"
                  "lib.libfilename.sql"
                  "lib.libgenre.sql"
                  "lib.libgenrelist.sql"
                  "lib.libjoinedbooks.sql"
                  "lib.librate.sql"
                  "lib.librecs.sql"
                  "lib.libseqname.sql"
                  "lib.libseq.sql")

downloadSQL() {
  local filename=$1
  curl -s https://flibusta.site/sql/$filename --output $DB_DIR/$filename && gunzip -f $DB_DIR/$filename
  ret_code=$?
  return $ret_code
}

mkdir -p $DB_DIR
downloaded=1

for name in "${files[@]}"; do
  downloaded=$downloaded && downloadSQL "$name.gz"
done

if [[ $downloaded ]]; then
  cat $DB_DIR/*.sql | mysql --password=$DB_ROOT_PASSWORD --host=$DB_HOST $DB_NAME
else
  echo "Wasn't downloaded"
fi