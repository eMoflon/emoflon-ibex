package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.List;

public interface Node {
    public String getType();

    public String getName();

    public Domain getDomain();

    public Action getAction();

    public List<String> getAttributes();
    
    public void setDomain(Domain pDomain);
    public void setAction(Action pAction);
}
