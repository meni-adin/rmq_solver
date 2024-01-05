FROM ubuntu:noble-20231221
SHELL ["/bin/bash", "-c"]

RUN echo "APT::Get::Assume-Yes \"true\";" > /etc/apt/apt.conf.d/90never-ask
RUN cat /etc/apt/apt.conf.d/90never-ask && sleep 5
RUN apt update
RUN apt-get install cmake=3.27.8-1build1
RUN apt-get install python3=3.11.4-5
RUN apt-get install zip=3.0-13
RUN apt-get install curl=8.4.0-2ubuntu1
RUN curl -s "https://get.sdkman.io" | bash
RUN source $HOME/.sdkman/bin/sdkman-init.sh \
    && sdk install java 21.0.1-oracle \
    && sdk default java 21.0.1-oracle \
    && sdk install gradle 8.6-rc-1 \
    && sdk default gradle 8.6-rc-1
RUN apt-get install tzdata=2023d-1ubuntu2
ENV TZ=Asia/Jerusalem
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /root/
COPY src src/

CMD echo -e "Usage:\nrun\nscripts/run-dev-container.sh\nthen\npython3 src/main.py"
