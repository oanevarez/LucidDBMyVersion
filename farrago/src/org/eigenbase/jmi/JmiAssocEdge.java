/*
// $Id$
// Package org.eigenbase is a class library of data management components.
// Copyright (C) 2005-2005 The Eigenbase Project
// Copyright (C) 2005-2005 Disruptive Tech
// Copyright (C) 2005-2005 Red Square, Inc.
//
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the Free
// Software Foundation; either version 2 of the License, or (at your option)
// any later version approved by The Eigenbase Project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package org.eigenbase.jmi;

import org._3pq.jgrapht.edge.*;

import javax.jmi.model.*;
import javax.jmi.reflect.*;

import java.util.*;

/**
 * JmiAssocEdge represents an association in a JMI model.  The source vertex is
 * the class for the source end and the target vertex is the class for the
 * target end, where
 *
 *<ul>
 *<li>if an end is composite, it is the source end
 *<li>if an end has multiplicity > 1, it is the target end if the other
 * end has multiplicity <= 1
 *<li>otherwise, the first end is the source end and the second end is
 * the target end
 *</ul>
 *
 * @author John V. Sichi
 * @version $Id$
 */
public class JmiAssocEdge extends DirectedEdge
{
    private final Association mofAssoc;

    private final AssociationEnd mofAssocEnds[];

    RefAssociation refAssoc;

    JmiAssocEdge(
        Association mofAssoc,
        JmiClassVertex source,
        JmiClassVertex target,
        AssociationEnd [] mofAssocEnds)
    {
        super(
            source,
            target);
        this.mofAssoc = mofAssoc;
        this.mofAssocEnds = mofAssocEnds;
    }

    /**
     * @return the MOF association represented by this edge
     */
    public Association getMofAssoc()
    {
        return mofAssoc;
    }

    /**
     * @return the RefAssociation represented by this edge
     */
    public RefAssociation getRefAssoc()
    {
        return refAssoc;
    }

    /**
     * @return the AssociationEnd for the source end
     */
    public AssociationEnd getSourceEnd()
    {
        return getEnd(0);
    }

    /**
     * @return the AssociationEnd for the target end
     */
    public AssociationEnd getTargetEnd()
    {
        return getEnd(1);
    }

    /**
     * Retrieves an end of the association.
     *
     * @param iEnd ordinal of end to get (0 for source, 1 for target)
     *
     * @return the requested AssociationEnd
     */
    public AssociationEnd getEnd(int iEnd)
    {
        return mofAssocEnds[iEnd];
    }

    // implement Object
    public String toString()
    {
        return mofAssocEnds[0].getType().getName() + ":"
            + mofAssocEnds[0].getName()
            + "_" + mofAssoc.getName() + "_"
            + mofAssocEnds[1].getType().getName() + ":"
            + mofAssocEnds[1].getName();
    }
}

// End JmiAssocEdge.java