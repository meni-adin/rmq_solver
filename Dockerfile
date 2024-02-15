FROM ubuntu:noble-20231221
SHELL ["/bin/bash", "-c"]

RUN echo "APT::Get::Assume-Yes \"true\";" > /etc/apt/apt.conf.d/90never-ask
RUN apt update
RUN apt-get install cmake
RUN apt-get install python3
RUN apt-get install zip
RUN apt-get install curl
RUN curl -s "https://get.sdkman.io" | bash
RUN source $HOME/.sdkman/bin/sdkman-init.sh \
    && sdk install java 21.0.1-oracle \
    && sdk default java 21.0.1-oracle \
    && sdk install gradle
RUN apt-get install tzdata
ENV TZ=Asia/Jerusalem
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /root/
COPY src src/

CMD echo -e "Usage:\nrun\nscripts/run-dev-container.sh\nthen\npython3 src/main.py"
