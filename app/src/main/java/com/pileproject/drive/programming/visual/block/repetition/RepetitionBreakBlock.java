/*
 * Copyright (C) 2011-2015 PILE Project, Inc. <dev@pileproject.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pileproject.drive.programming.visual.block.repetition;

import android.content.Context;
import android.view.LayoutInflater;

import com.pileproject.drive.R;
import com.pileproject.drive.execution.ExecutionCondition;
import com.pileproject.drive.execution.MachineController;
import com.pileproject.drive.programming.visual.block.BlockBase;

/**
 * A block that expresses a break of while loop.
 */
public class RepetitionBreakBlock extends BlockBase {
    public RepetitionBreakBlock(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.block_repetition_break, this);
    }

    @Override
    public Class<? extends BlockBase> getKind() {
        return RepetitionBreakBlock.class;
    }

    @Override
    public int action(MachineController controller, ExecutionCondition condition) {
        condition.breakLoop();
        return 0;
    }
}
