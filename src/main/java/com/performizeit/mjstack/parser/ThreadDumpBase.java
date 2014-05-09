/*
       This file is part of mjprof.

        mjprof is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        mjprof is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with mjprof.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.performizeit.mjstack.parser;

import com.performizeit.mjstack.api.DumpMapper;
import com.performizeit.mjstack.api.JStackFilter;
import com.performizeit.mjstack.api.JStackMapper;
import com.performizeit.mjstack.api.JStackTerminal;
import com.performizeit.mjstack.model.JStackHeader;

import java.util.Comparator;

public abstract class ThreadDumpBase {
    protected JStackHeader header;
    protected ThreadDumpBase() {

    }

    public abstract ThreadDumpBase filterDump(JStackFilter filter) ;
    public abstract ThreadDumpBase mapDump(JStackMapper mapper);
    public abstract ThreadDumpBase mapDump(DumpMapper mapper);
    public abstract ThreadDumpBase sortDump(Comparator<ThreadInfo> comp);
    public abstract ThreadDumpBase terminateDump(JStackTerminal terminal);

    public JStackHeader getHeader() {
        return header;
    }
}
