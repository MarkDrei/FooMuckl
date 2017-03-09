package de.rkable.foomuckl.core.action;

/**
 * This Output represents the simply doing nothing
 * @author Mark
 *
 */
public class DoNothing implements Action {

	int foo = 1;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + foo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoNothing other = (DoNothing) obj;
		if (foo != other.foo)
			return false;
		return true;
	}
}
