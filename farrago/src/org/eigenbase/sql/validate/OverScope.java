/*
// $Id$
// Package org.eigenbase is a class library of data management components.
// Copyright (C) 2004-2005 The Eigenbase Project
// Copyright (C) 2004-2005 Disruptive Tech
// Copyright (C) 2005-2005 LucidEra, Inc.
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
package org.eigenbase.sql.validate;

import org.eigenbase.sql.*;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * The name-resolution scope of a OVER clause. The objects visible are
 * those in the parameters found on the left side of the over clause, and
 * objects inherited from the parent scope.
 * <p/>
 * <p>This object is both a {@link SqlValidatorScope} only.
 * In the query
 * <p/>
 * <blockquote>
 * <pre>SELECT name FROM (
 *     SELECT *
 *     FROM emp over (order by empno range between 2 preceding and 2 following)
 * <p/>
 * <p>we need to use the {@link OverScope} as a
 * {@link SqlValidatorNamespace} when resolving names used in the window
 * specification.
 * <p/>
 *
 * @author jack
 * @version $Id$
 * @since Mar 25, 2003
 */
public class OverScope extends ListScope
{
    private final SqlCall overCall;

    /**
     * Creates a scope corresponding to a SELECT clause.
     *
     * @param parent Parent scope, or null
     * @param overCall
     */
    OverScope(SqlValidatorScope parent,
        SqlCall overCall)
    {
        super(parent);
        this.overCall = overCall;
    }

    /**
     * Find out all the valid alternatives for the operand of this node's
     * operator that matches the parse position indicated by pos
     *
     * @return a {@link SqlNode} base of the Over operator subtree
     */
    public SqlNode getNode()
    {
        return overCall;
    }

    /**
     *  Test if this node is monontic when applied in this scope
     * 
     * @param expr is the base node of a Sql expression to be checked
     * @return boolean value.  TRUE if expr is Monotonic
     */
    public boolean isMonotonic(SqlNode expr)
    {
        if (expr.isMonotonic(this)) {
            return true;
        }

        if (children.size() == 1) {
            final SqlNodeList monotonicExprs =
                ((SqlValidatorNamespace) children.get(0)).getMonotonicExprs();
            for (int i = 0; i < monotonicExprs.size(); i++) {
                SqlNode monotonicExpr = monotonicExprs.get(i);
                if (expr.equalsDeep(monotonicExpr)) {
                    return true;
                }
            }
        }
        return super.isMonotonic(expr);
    }
}

// End SelectScope.java