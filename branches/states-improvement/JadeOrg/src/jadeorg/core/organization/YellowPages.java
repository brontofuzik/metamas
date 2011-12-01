package jadeorg.core.organization;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The JadeOrg DF service wrapper.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class YellowPages {
    
    public static DFAgentDescription searchOrganizationWithRole(Agent searcher, String organizationName, String roleName) {
        DFAgentDescription organizationTemplate = createOrganizationTemplate();
        
        DFAgentDescription[] candidateOrganizations = null;			
        try {
            candidateOrganizations = jade.domain.DFService.search(searcher, organizationTemplate);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
    
        for (DFAgentDescription candidateOrganization : candidateOrganizations) {
            
            String candidateOrganizationName = getAgentName(candidateOrganization);				
            if (candidateOrganizationName.equals(organizationName)) {
                
                List<ServiceDescription> candidateOrganizationServices = getAgentServices(candidateOrganization);
                for (ServiceDescription service : candidateOrganizationServices) {
                   if (service.getName().equals(roleName)) {
                       return candidateOrganization;
                   } 
                }
            }
        }
        return null;
    }
    
    private static DFAgentDescription createOrganizationTemplate() {
        DFAgentDescription organizationTemplate = new DFAgentDescription();
        
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("role");
        organizationTemplate.addServices(serviceDescription);
        
        return organizationTemplate;
    }
    
    private static String getAgentName(DFAgentDescription agentDescription) {
        StringTokenizer tokenizer = new StringTokenizer((agentDescription.getName()).getName(), "@");
        String localName = tokenizer.nextToken();
        return localName;
    }
    
    // TODO Replace the List interface with Java-equivalent of .NET's IEnumerable interface.
    private static List<ServiceDescription> getAgentServices(DFAgentDescription agentDescription) {
        List<ServiceDescription> serviceDescriptions = new ArrayList<ServiceDescription>();
        Iterator iterator = agentDescription.getAllServices();
        while (iterator.hasNext()) {
            ServiceDescription serviceDescription = (ServiceDescription)iterator.next();
            serviceDescriptions.add(serviceDescription);
        }
        return serviceDescriptions;
    }
}
