#!/bin/sh
### BEGIN INIT INFO
# Provides:          house_server
# Required-Start:    $remote_fs $syslog
# Required-Stop:     $remote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Starts and stops the python server
# Description:       Starts and stops the python server
### END INIT INFO

NAME="house_server"
PY_PATH="/Proyecto1/Server.py" #replace with the actual path
PID_FILE="/var/run/$NAME.pid"

start() {
  echo "Starting $NAME"
  'root' -c 'python3 /Proyecto1/Server.py&'
  echo $! >$PID_FILE
}

stop() {
  echo "Stopping $NAME"
  kill $(cat $PID_FILE)
  rm $PID_FILE
}

status() {
  if [ -f $PID_FILE ]; then
    PID=$(cat $PID_FILE)
    if ps -p $PID >/dev/null; then
      echo "$NAME is running with PID $PID"
    else
      echo "$NAME is not running"
    fi
  else
    echo "$NAME is not running"
  fi
}

case "$1" in
start)
  start
  ;;
stop)
  stop
  ;;
restart)
  stop
  start
  ;;
status)
  status
  ;;
*)
  echo "Usage: $0 {start|stop|restart|status}"
  exit 1
  ;;
esac

exit 0

