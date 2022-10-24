#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=book

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
#CURRENT_PID=$(pgrep -fl book | grep jar | awk '{print $1}')
CURRENT_PID=$(pgrep -f $PROJECT_NAME)

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> Kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"
#nohup java -jar \
#  -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
#  -Dspring.profiles.active=real \
#  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
  -Dspring.profile.active=real \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

# [$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &]
# -nohup 실행 시 CodeDeploy는 무한 대기
# -위 이슈 해결을 위해 nohup.out 파일을 표준 입출력용으로 별도 사용
# -이렇게 하지 않으면 nohup.out 파일이 생기지 않고, CodeDeploy 로그에 표준 입출력이 출력됨
# -nohup이 끝나기 전까지는 CodeDeploy도 끝나지 않으니 꼭 이렇게!