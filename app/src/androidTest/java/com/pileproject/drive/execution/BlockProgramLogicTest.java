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

package com.pileproject.drive.execution;

import android.support.test.runner.AndroidJUnit4;

import com.pileproject.drive.programming.visual.block.BlockBase;
import com.pileproject.drive.programming.visual.block.selection.SelectionBlock;
import com.pileproject.drive.programming.visual.block.selection.SelectionEndBlock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class BlockProgramLogicTest {

    @Mock ExecutionCondition.SelectionResult trueResult;
    @Mock ExecutionCondition.SelectionResult falseResult;

    @Mock SelectionBlock selectionBlock;

    @Mock BlockBase trueBlock;
    @Mock BlockBase falseBlock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Whitebox.setInternalState(trueResult, "result", true);
        Whitebox.setInternalState(falseResult, "result", false);

        // NOTE: commented out lines are using final methods
        // TODO: consider using PowerMockito

        // doReturn(0).when(selectionBlock).getLeft();
        // doReturn(199).when(selectionBlock).getRight();
        Whitebox.setInternalState(selectionBlock, "mLeft", 0);
        Whitebox.setInternalState(selectionBlock, "mRight", 199);

        // doReturn(0).when(trueBlock).getLeft();
        // doReturn(99).when(trueBlock).getRight();
        Whitebox.setInternalState(trueBlock, "mLeft", 0);
        Whitebox.setInternalState(trueBlock, "mRight", 99);

        // doReturn(100).when(falseBlock).getLeft();
        // doReturn(199).when(falseBlock).getRight();
        Whitebox.setInternalState(falseBlock, "mLeft", 100);
        Whitebox.setInternalState(falseBlock, "mRight", 199);
    }

    @Test
    public void whenExecuteConditionHasNoSelectionResult_thenExecuteCurrentBlock() throws Exception {

        ExecutionCondition condition = mock(ExecutionCondition.class);
        doReturn(0).when(condition).sizeOfSelectionResult(); // no selection result


        assertTrue(BlockProgramLogic.willCurrentBlockBeExecuted(condition));


        verify(condition).sizeOfSelectionResult();  // do call
        verify(condition, never()).getCurrentBlock(); // do not call
    }

    @Test
    public void whenCurrentBlockIsSelectionEnd_thenExecuteCurrentBlock() throws Exception {

        ExecutionCondition condition = mock(ExecutionCondition.class);

        doReturn(1).when(condition).sizeOfSelectionResult(); // this means pushing a selection result

        doReturn(mock(SelectionEndBlock.class)).when(condition).getCurrentBlock(); // return a SelectionEndBlock


        assertTrue(BlockProgramLogic.willCurrentBlockBeExecuted(condition));


        verify(condition).getCurrentBlock(); // do call
        verify(condition, never()).peekSelectionResult(); // do not call
    }

    @Test
    public void whenSelectionResultWasTrue_andCurrentBlockIsInsideOfTrueStatement_thenExecuteCurrentBlock()
            throws Exception {
        ExecutionCondition condition = mock(ExecutionCondition.class);

        doReturn(1).when(condition).sizeOfSelectionResult(); // this means pushing a selection result

        doReturn(trueBlock).when(condition).getCurrentBlock(); // return a block inside a true statement

        doReturn(trueResult).when(condition).peekSelectionResult(); // return true result

        // return a nearest Selection bock
        doReturn(selectionBlock).when(condition)
                .getNearestSelectionBlock(any(ExecutionCondition.SelectionResult.class));


        assertTrue(BlockProgramLogic.willCurrentBlockBeExecuted(condition));


        verify(condition).peekSelectionResult(); // do call
    }

    @Test
    public void whenSelectionResultWasTrue_andCurrentBlockIsInsideOfFalseStatement_thenDoNotExecuteCurrentBlock()
            throws Exception {
        ExecutionCondition condition = mock(ExecutionCondition.class);

        doReturn(1).when(condition).sizeOfSelectionResult(); // this means pushing a selection result

        doReturn(falseBlock).when(condition).getCurrentBlock(); // return a block inside a false statement

        doReturn(trueResult).when(condition).peekSelectionResult(); // return true result

        // return a nearest Selection bock
        doReturn(selectionBlock).when(condition)
                .getNearestSelectionBlock(any(ExecutionCondition.SelectionResult.class));


        assertFalse(BlockProgramLogic.willCurrentBlockBeExecuted(condition));


        verify(condition).peekSelectionResult(); // do call
    }

    @Test
    public void whenSelectionResultWasFalse_andCurrentBlockIsInsideOfTrueStatement_thenDoNotExecuteCurrentBlock()
            throws Exception {
        ExecutionCondition condition = mock(ExecutionCondition.class);

        doReturn(1).when(condition).sizeOfSelectionResult(); // this means pushing a selection result

        doReturn(trueBlock).when(condition).getCurrentBlock(); // return a block inside a true statement

        doReturn(falseResult).when(condition).peekSelectionResult(); // return false result

        // return a nearest Selection bock
        doReturn(selectionBlock).when(condition)
                .getNearestSelectionBlock(any(ExecutionCondition.SelectionResult.class));


        assertFalse(BlockProgramLogic.willCurrentBlockBeExecuted(condition));


        verify(condition).peekSelectionResult(); // do call
    }

    @Test
    public void whenSelectionResultWasFalse_andCurrentBlockIsInsideOfFalseStatement_thenExecuteCurrentBlock()
            throws Exception {
        ExecutionCondition condition = mock(ExecutionCondition.class);

        doReturn(1).when(condition).sizeOfSelectionResult(); // this means pushing a selection result

        doReturn(falseBlock).when(condition).getCurrentBlock(); // return a block inside a false statement

        doReturn(falseResult).when(condition).peekSelectionResult(); // return false result

        // return a nearest Selection bock
        doReturn(selectionBlock).when(condition)
                .getNearestSelectionBlock(any(ExecutionCondition.SelectionResult.class));


        assertTrue(BlockProgramLogic.willCurrentBlockBeExecuted(condition));


        verify(condition).peekSelectionResult(); // do call
    }
}