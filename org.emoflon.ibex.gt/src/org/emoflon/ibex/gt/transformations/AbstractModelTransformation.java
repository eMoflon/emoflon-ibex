package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a super class for model transformations.
 *
 * @param <SourceModel>
 *            the type of the source model
 * @param <TargetModel>
 *            the type of the target model
 */
abstract public class AbstractModelTransformation<SourceModel, TargetModel> {
	/**
	 * The errors occurred during the transformation.
	 */
	private List<String> errors;

	/**
	 * Creates a new AbstractModelTransformation.
	 */
	public AbstractModelTransformation() {
		this.errors = new ArrayList<String>();
	}

	/**
	 * Transforms the source into the target model.
	 * 
	 * @param sourceModel
	 *            the source model, must not be <code>null</code>
	 * @return the target model
	 */
	abstract public TargetModel transform(final SourceModel sourceModel);

	/**
	 * Logs an error.
	 * 
	 * @param error
	 *            the error to log
	 */
	protected final void logError(final String error) {
		errors.add(error);
	}

	/**
	 * Logs an error.
	 * 
	 * @param error
	 *            the error
	 * @param args
	 *            arguments for String.format
	 */
	protected final void logError(final String error, final Object... args) {
		errors.add(String.format(error, args));
	}

	/**
	 * Returns the errors occurred during the transformation.
	 * 
	 * @return the error messages
	 */
	public final List<String> getErrors() {
		return errors;
	}

	/**
	 * Returns the number of errors occurred during the transformation.
	 * 
	 * @return the number of errors
	 */
	public final int countErrors() {
		return errors.size();
	}

	/**
	 * Returns whether errors occurred during the transformation
	 * 
	 * @return <code>false</code> if no errors occurred during the transformation
	 */
	public final boolean hasErrors() {
		return !errors.isEmpty();
	}
}
