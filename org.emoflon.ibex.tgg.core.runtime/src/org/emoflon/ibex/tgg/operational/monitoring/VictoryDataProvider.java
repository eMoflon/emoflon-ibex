package org.emoflon.ibex.tgg.operational.monitoring;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.TGGRule;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class VictoryDataProvider implements IVictoryDataProvider {

	private final static Logger logger = Logger.getLogger(VictoryDataProvider.class);

	String[][] defaultSaveData = new String[4][1];
	
	OperationalStrategy op;

	public VictoryDataProvider(OperationalStrategy pOperationalStrategy) {
		this.op = pOperationalStrategy;
	}

	@Override
	public TGGRule getRule(String pRuleName) {
		try {
			return op.getOptions().flattenedTGG().getRules().stream().filter(r -> r.getName().equals(pRuleName))
					.findFirst().get();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
    public Collection<EObject> getMatchNeighbourhoods(Collection<EObject> nodes, int k) {
        try {
            Collection<EObject> neighbors = new HashSet<EObject>();
            for (EObject node : nodes) {
                neighbors.addAll(getMatchNeighbourhood(node, k));
            }
            return neighbors;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Collection<EObject> getMatchNeighbourhood(EObject node, int k) {
        try {
            Set<EObject> neighbors = new HashSet<EObject>();
            if (k >= 0) {
                neighbors.add(node);

                if (k > 0) {
                    calculateNeighbourhood(neighbors, node, 0, k);
                }
            }

            return neighbors;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    private void calculateNeighbourhood(Collection<EObject> neighbors, EObject node, int i, int k) {

        i++;
        Set<EObject> currentNeighbors = new HashSet<EObject>();
        currentNeighbors = node.eContents().stream().collect(Collectors.toSet());
        neighbors.addAll(currentNeighbors);
        Iterator<EObject> cn = currentNeighbors.iterator();
        if (i < k) {
            while (cn.hasNext()) {
                calculateNeighbourhood(neighbors, cn.next(), i, k);
            }
        }

    } 

	@Override
	public Set<IMatch> getMatches() {
		try {
			return op.getMatchContainer().getMatches();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Set<IMatch> getMatches(String pRuleName) {
		try {
			return this.getMatches().stream().filter(r -> r.getRuleName().equals(pRuleName))
					.collect(Collectors.toSet());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Set<IMatch> getMatches(IMatch match) {
		try {
			return this.getMatches(match.getRuleName());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public void saveModels(String[] pLocations) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
		Date date = new Date(System.currentTimeMillis());
		String time = dateFormat.format(date).toString();
		LinkedHashMap<String, Resource> resources = new LinkedHashMap<String, Resource>();
		LinkedHashMap<String, URI> oldUri = new LinkedHashMap<String, URI>();

		// storing resources that needs to be saved
		resources.put("s", op.getSourceResource());
		resources.put("t", op.getTargetResource());
		resources.put("c", op.getCorrResource());
		resources.put("p", op.getProtocolResource());

		// save models
		for (Entry<String, Resource> e : resources.entrySet()) {
			oldUri.put(e.getKey(), e.getValue().getURI());
			saveModel(e.getValue(), time);
		}

		// revert the URIs to before saving models
		for (Entry<String, URI> e : oldUri.entrySet()) {
			resources.get(e.getKey()).setURI(e.getValue());
		}

		for (int i=0; i < pLocations.length; i++ ) {
			String extension = "." + FilenameUtils.getExtension(pLocations[i]);
			String path = FilenameUtils.getPath(pLocations[i]);
			String fileName = FilenameUtils.getBaseName(pLocations[i]);
			
			defaultSaveData[i] = new String[] { path, fileName, extension };	
		}
		
		
		for (String[] e: defaultSaveData) {
			System.out.println("DefaultSaveData123: " + Arrays.toString(e));
		}
	}

	private void saveModel(Resource r, String time) throws IOException {
		String path = r.getURI().toString();

		// generating new URI (name and path) base on old URI
		String newPath = FilenameUtils.getPath(path);
		
		newPath += FilenameUtils.getBaseName(path) + "-";
		newPath += time + "." + FilenameUtils.getExtension(path);
		URI newUri = URI.createURI(newPath);
		
		r.setURI(newUri);
		r.save(null);
		
		/*defaultSaveData = new String[][] {
            new String[] { "src/path/", "src", ".model" },
            new String[] { "trg/path/", "trg", ".model" },
            new String[] { "corr/path/", "corr", ".model" },
            new String[] { "protocol/path/", "protocol", ".model" } };*/
	}

	public String[][] getDefaultSaveData() {
	    // TODO
	    return defaultSaveData;
	}
	
	@Override
	public Collection<TGGRule> getAllRules() {
		return op.getOptions().flattenedTGG().getRules();
	}
}
