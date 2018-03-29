/**
 * 
 */
package org.emoflon.ibex.tgg.util.ilp;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.gnu.glpk.GLPK;

/**
 * @author RobinO
 *
 */
public final class GLPKWrapper extends ILPSolver {
	
	static {
		try {
			addLibraryPath("C:\\Program Files\\GLPK\\glpk-4.65\\w64");
			addLibraryPath("C:\\Program Files (x86)\\GLPK\\glpk-4.65\\w32");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	* Adds the specified path to the java library path
	*
	* @param pathToAdd the path to add
	* @throws Exception
	*/
	private static void addLibraryPath(String pathToAdd) throws Exception{
//		ClassLoader.getSystemClassLoader().
	    final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
	    usrPathsField.setAccessible(true);
	 
	    //get array of paths
	    final String[] paths = (String[])usrPathsField.get(null);
	 
	    //check if the path to add is already present
	    for(String path : paths) {
	        if(path.equals(pathToAdd)) {
	            return;
	        }
	    }
	 
	    //add the new path
	    final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
	    newPaths[newPaths.length-1] = pathToAdd;
	    usrPathsField.set(null, newPaths);
	}

	/**
	 * 
	 */
	public GLPKWrapper() {
		// TODO Auto-generated constructor stub
		System.out.println(GLPK.glp_version());
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#createLinearExpression(org.emoflon.ibex.tgg.util.ilp.ILPSolver.ILPTerm[])
	 */
	@Override
	public ILPLinearExpression createLinearExpression(ILPTerm... terms) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#addConstraint(org.emoflon.ibex.tgg.util.ilp.ILPSolver.ILPLinearExpression, org.emoflon.ibex.tgg.util.ilp.ILPSolver.Operation, double, java.lang.String)
	 */
	@Override
	public ILPConstraint addConstraint(ILPLinearExpression linearExpression, Operation comparator, double value,
			String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#setObjective(org.emoflon.ibex.tgg.util.ilp.ILPSolver.ILPLinearExpression, org.emoflon.ibex.tgg.util.ilp.ILPSolver.Operation)
	 */
	@Override
	public ILPObjective setObjective(ILPLinearExpression linearExpression, Operation operation) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#solveILP()
	 */
	@Override
	public ILPSolution solveILP() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
