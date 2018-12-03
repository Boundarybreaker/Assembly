/*
 * Copyright (c) 2018 modmuss50 and Gigabit101
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package teamreborn.assembly.client.container.builder;

import net.minecraft.container.FurnaceFuelSlot;
import net.minecraft.container.Slot;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.Pair;
import teamreborn.assembly.client.container.builder.slot.FilteredSlot;
import teamreborn.assembly.client.container.builder.slot.SlotOutput;
import teamreborn.assembly.util.ObjectConsumer;
import teamreborn.assembly.util.ObjectSupplier;

import java.util.function.*;

public class ContainerTileInventoryBuilder {

	private final Inventory tile;
	private final ContainerBuilder parent;
	private final int rangeStart;

	ContainerTileInventoryBuilder(final ContainerBuilder parent, final Inventory tile) {
		this.tile = tile;
		this.parent = parent;
		this.rangeStart = parent.slots.size();
	}

	public ContainerTileInventoryBuilder slot(final int index, final int x, final int y) {
		this.parent.slots.add(new Slot(this.tile, index, x, y));
		return this;
	}

	public ContainerTileInventoryBuilder outputSlot(final int index, final int x, final int y) {
		this.parent.slots.add(new SlotOutput(this.tile, index, x, y));
		return this;
	}



	public ContainerTileInventoryBuilder filterSlot(final int index, final int x, final int y,
	                                                final Predicate<ItemStack> filter) {
		this.parent.slots.add(new FilteredSlot(this.tile, index, x, y).setFilter(filter));
		return this;
	}


	public ContainerTileInventoryBuilder fuelSlot(final int index, final int x, final int y) {
		this.parent.slots.add(new FurnaceFuelSlot(this.tile, index, x, y));
		return this;
	}

	/**
	 * @param supplier The supplier it can supply a variable holding in an Object it
	 * will be synced with a custom packet
	 * @param setter The setter to call when the variable has been updated.
	 * @return ContainerTileInventoryBuilder Inventory which will do the sync
	 */
	public <T> ContainerTileInventoryBuilder sync(final ObjectSupplier<T> supplier, final ObjectConsumer<T> setter) {
		this.parent.objectValues.add(Pair.of(supplier, setter));
		return this;
	}


	public ContainerTileInventoryBuilder onCraft(final Consumer<CraftingInventory> onCraft) {
		this.parent.craftEvents.add(onCraft);
		return this;
	}

	public ContainerBuilder addInventory() {
		this.parent.tileInventoryRanges.add(Range.between(this.rangeStart, this.parent.slots.size() - 1));
		return this.parent;
	}
}