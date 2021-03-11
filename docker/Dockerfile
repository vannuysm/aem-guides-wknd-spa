FROM adoptopenjdk:11-jre-hotspot

ARG AEM_VERSION="6.5.0"
ARG AEM_JVM_OPTS="-server -Xms1024m -Xmx1024m -XX:MaxDirectMemorySize=256M -XX:+CMSClassUnloadingEnabled -Djava.awt.headless=true -Dorg.apache.felix.http.host=0.0.0.0 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=58242"
ARG AEM_START_OPTS="start -c /aem/crx-quickstart -i launchpad -p 8080 -a 0.0.0.0 -Dsling.properties=conf/sling.properties"
ARG AEM_JARFILE="/aem/crx-quickstart/app/cq-quickstart-${AEM_VERSION}-standalone-quickstart.jar"
ARG AEM_RUNMODE="-Dsling.run.modes=author,crx3,crx3tar,nosamplecontent"

ENV AEM_JVM_OPTS="${AEM_JVM_OPTS}" \
    AEM_START_OPTS="${AEM_START_OPTS}"\
    AEM_JARFILE="${AEM_JARFILE}" \
    AEM_RUNMODE="${AEM_RUNMODE}"

WORKDIR /aem

COPY downloads/license.properties ./
COPY downloads/AEM_Quickstart.jar ./aem-quickstart.jar

RUN java -jar aem-quickstart.jar -unpack && \
    rm aem-quickstart.jar

COPY downloads/packages/ ./crx-quickstart/install/

COPY init.sh ./
RUN chmod +x ./init.sh

EXPOSE 8080 58242

VOLUME ["/aem/crx-quickstart"]

ENTRYPOINT ["/bin/bash", "/aem/init.sh"]