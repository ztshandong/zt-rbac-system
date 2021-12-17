# upload package name
NAMEDEPLOY='rbacsystem-0.0.1-SNAPSHOT.jar'
# exists package name
NAMEBEFORE='rbacsystem-0.0.1.jar'

# mark the time as
ymd=`date +%Y-%m-%d-%H-%M-%S`;
historyTime=$(date "+%Y-%m-%d-%H-%M-%S" -d '2 month ago')

# stop the process before
ID=`ps -ef | grep "$NAMEBEFORE" | grep -v "grep" | awk '{print $2}'`
echo The process pid is $ID
for id in $ID
do
    kill -15 $id
    echo killed $id
done

# backup package
mv $NAMEBEFORE $NAMEBEFORE.bak-$ymd
mv $NAMEDEPLOY $NAMEBEFORE
sleep 5
nohup java -jar $NAMEBEFORE >> nohup.out &
