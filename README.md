PhysUSP
=======

A web-based java/tomcat software to estimate the energy expenditure and
energy system contributions during the exercise using the measurement
of oxygen uptake and the blood lactate accumulation.

The corresponding research paper is [Bertuzzi et al. (2016).
GEDAE-LaB: A Free Software to Calculate the Energy System
Contributions during Exercise. PLoS ONE 11 (1):
e0145733](http://journals.plos.org/plosone/article?id=10.1371/journal.pone.0145733).

You may try this software at <http://gedaelab.org>. If you want to,
it is not hard to install this on your own machine/desktop:

 * Install java, maven, and tomcat
 * Download the software source code
 * In the physusp directory (where the `pom.xml` file is), run `mvn package`
 * Copy the generated `physusp-1.0.war` file from the `target` directory
   to the `webapps` tomcat directory
 * Wait for a few seconds
 * Point your browser to <http://localhost:8080/physusp-1.0>

There is a simplistic ansible playbook to install in debian-based systems.
