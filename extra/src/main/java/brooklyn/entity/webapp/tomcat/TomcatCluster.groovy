package brooklyn.entity.webapp.tomcat;

import java.util.Map

import brooklyn.entity.Group
import brooklyn.entity.group.ClusterFromTemplate

public class TomcatCluster extends ClusterFromTemplate {
    public TomcatCluster(Map props=[:], TomcatNode template=new TomcatNode()) {
        super(props, template);
    }
}