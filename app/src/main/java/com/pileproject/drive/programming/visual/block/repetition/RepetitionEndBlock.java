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
 * This block is the end of while loop
 *
 * @author <a href="mailto:tatsuyaw0c@gmail.com">Tatsuya Iwanari</a>
 * @version 1.0 7-July-2013
 */
public class RepetitionEndBlock extends BlockBase {

    public RepetitionEndBlock(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.block_repetition_end, this);
    }

    @Override
    public Class<? extends BlockBase> getKind() {
        return RepetitionEndBlock.class;
    }

    @Override
    public int action(MachineController controller, ExecutionCondition condition) {

        if (!condition.whileStack.isEmpty()) {
            // Check the pair of this RepetitionEndBlock is  WhileForeverBlock
            // Not ForeverWhileBlock
            if (condition.whileStack.peek() >= 0) {
                // Check the while loop had already finished or not
                // Had already finished
                if (condition.beginningOfCurrentWhileLoop != condition.whileStack.peek()) {
                    // just update the index of the beginning of current while loop
                    condition.beginningOfCurrentWhileLoop = condition.whileStack.peek();
                }
                // Had not finished yet
                else {
                    // Go back to the beginning of current while loop
                    condition.programCount = condition.whileStack.pop();
                }
            }
            // ForeverWhile
            else {
                int indexWithoutOffset = condition.whileStack.peek() - WhileForeverBlock.FOREVER_WHILE_OFFSET;
                // Check the while loop had already finished or not
                // Had already finished
                if (condition.beginningOfCurrentWhileLoop != indexWithoutOffset) {
                    // just update the index of the beginning of current while loop
                    condition.beginningOfCurrentWhileLoop = indexWithoutOffset;
                }
                // Had not finished yet
                else {
                    // Go back to the beginning of current while loop
                    // with excluding the offset
                    condition.programCount = indexWithoutOffset;
                }
            }
        }
        return 1;
    }
}
