package com.thinkbiganalytics.metadata.modeshape.datasource;

import com.thinkbiganalytics.metadata.api.datasource.DatasourceDefinition;
import com.thinkbiganalytics.metadata.api.datasource.DerivedDatasource;
import com.thinkbiganalytics.metadata.modeshape.support.JcrPropertyUtil;
import com.thinkbiganalytics.metadata.modeshape.support.JcrUtil;

import java.util.Set;
import java.util.stream.Collectors;

import javax.jcr.Node;

/**
 * Created by sr186054 on 11/10/16.
 */
public class JcrDerivedDatasource extends JcrDatasource implements DerivedDatasource {

    public static final String NODE_TYPE = "tba:derivedDatasource";

    public static final String DATASOURCE_DEFINITION = "tba:datasourceDefinition";


    public static final String TYPE_NAME = "tba:datasourceType";

    @SuppressWarnings("unused")
    private static final String PATH_NAME = "derivedDatasource";


    public JcrDerivedDatasource(Node node) {
        super(node);
    }


    @Override
    public Set<DatasourceDefinition> getDatasourceDefinitions() {
        return JcrPropertyUtil.getReferencedNodeSet(this.node, DATASOURCE_DEFINITION).stream()
            .map(n -> JcrUtil.createJcrObject(n, JcrDatasourceDefinition.class))
            .collect(Collectors.toSet());
    }


    public void setDatasourceDefinitions(Set<DatasourceDefinition> datasourceDefinitions) {
        JcrPropertyUtil.setProperty(this.node, DATASOURCE_DEFINITION, null);

        for (DatasourceDefinition dest : datasourceDefinitions) {
            Node destNode = ((JcrDatasourceDefinition) dest).getNode();
            addDatasourceDefinition(destNode);
        }
    }


    public void addDatasourceDefinition(Node node) {
        JcrPropertyUtil.addToSetProperty(this.node, DATASOURCE_DEFINITION, node, true);
    }

    public void setDatasourceType(String type) {
        JcrPropertyUtil.setProperty(this.node, TYPE_NAME, type);
    }

    public String getDatasourceType() {
        return JcrPropertyUtil.getProperty(this.node, TYPE_NAME);
    }

}