package org.moflon.tgg.mosl.ui.highlighting;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.util.Arrays;

import com.google.inject.Inject;

public class MOSLTokenMapper extends DefaultAntlrTokenToAttributeIdMapper {

	public MOSLTokenMapper(){
		super();
		init();
	}
	
	private void init() {
		mappedTokens.clear();		
	}

	private static Map<String, String> mappedTokens = new HashMap<>();
	
	@Inject
	static MOSLTokenMapper mapper = new MOSLTokenMapper();
	
	private static final String[] delemiters = {":","{","}","(",")"};
	private static final String[] enumerationDelemiters ={"::","enum::"};
	private static final String[] sourceTokens = {"#src->", "#source"};
	private static final String[] targetTokens = {"#trg->", "#target"};
	
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		String trimmedTokenName = tokenName.replaceAll("'", "");
		String id = super.calculateId(tokenName, tokenType);		
		if ("RULE_BOOL".equals(tokenName)) {
			id = MOSLHighlightingConfiguration.BOOL_ID;
		}
		if(Arrays.contains(delemiters, trimmedTokenName)){
			id = MOSLHighlightingConfiguration.DEFAULT_ID;
		}
		if(Arrays.contains(enumerationDelemiters, trimmedTokenName)){
			id = MOSLHighlightingConfiguration.ENUM_ID;
		}
		if(Arrays.contains(sourceTokens, trimmedTokenName)){
			id = MOSLHighlightingConfiguration.SOURCE_ID;
		}
		if(Arrays.contains(targetTokens, trimmedTokenName)){
			id = MOSLHighlightingConfiguration.TARGET_ID;
		}
		mappedTokens.put(trimmedTokenName, id);
		return id;
	}
	
	public static String getIDFromToken(String token){
		String trimmedTokenName = token.replaceAll("'", "");
		return mappedTokens.get(trimmedTokenName);
	}
}
