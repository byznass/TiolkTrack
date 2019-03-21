FROM gradle:alpine AS war_builder

USER root
RUN mkdir /TiolkTrack && chown gradle:gradle /TiolkTrack
USER gradle

WORKDIR /TiolkTrack
COPY --chown=gradle . ./
RUN gradle war

FROM jetty
COPY --from=war_builder --chown=jetty /TiolkTrack/build/libs/TiolkTrack-0.1.war /var/lib/jetty/webapps/ROOT.war