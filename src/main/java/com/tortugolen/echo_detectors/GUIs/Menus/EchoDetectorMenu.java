package com.tortugolen.echo_detectors.GUIs.Menus;

import com.tortugolen.echo_detectors.Init.InitItems;
import com.tortugolen.echo_detectors.Init.InitMenus;
import com.tortugolen.echo_detectors.Items.EchoDetectorItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class EchoDetectorMenu extends AbstractContainerMenu {
    private final ItemStack echoDetectorItem;
    private final Container container;
    private final int blockedSlot;

    public EchoDetectorMenu(int pContainerId, Inventory pInventory, FriendlyByteBuf pFriendlyByteBuf) {
        this(pContainerId, pInventory, pFriendlyByteBuf.readItem(), new SimpleContainerData(2));
    }

    public EchoDetectorMenu(int pContainerId, Inventory pInventory, ItemStack pDetectorStack, ContainerData pData) {
        super(InitMenus.ECHO_DETECTOR_MENU.get(), pContainerId);

        this.echoDetectorItem = pDetectorStack;
        this.container = new SimpleContainer(1) {
            @Override
            public void setChanged() {
                super.setChanged();
                EchoDetectorItem.setStoredItem(echoDetectorItem, this.getItem(0));
            }
        };

        this.blockedSlot = findDetectorSlot(pInventory);

        ItemStack storedItem = EchoDetectorItem.getStoredItem(pDetectorStack);
        if (!storedItem.isEmpty()) {
            this.container.setItem(0, storedItem);
        }

        this.addSlot(new Slot(container, 0, 80, 30) {
            @Override
            public void setChanged() {
                super.setChanged();
                container.setChanged();
            }

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(InitItems.IRON_FILTER.get()) ||
                        pStack.is(InitItems.GOLD_FILTER.get()) ||
                        pStack.is(InitItems.COPPER_FILTER.get()) ||
                        pStack.is(InitItems.NETHERITE_FILTER.get());
            }
        });

        addPlayerInventory(pInventory);
        addPlayerHotbar(pInventory);

        addDataSlots(pData);
    }

    private int findDetectorSlot(Inventory pInventory) {
        for (int i = 0; i < pInventory.getContainerSize(); i++) {
            if (pInventory.getItem(i) == echoDetectorItem) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clicked(int pSlotId, int pButton, ClickType pClick, Player pPlayer) {
        if (isDetectorSlot(pSlotId)) {
            return;
        }
        super.clicked(pSlotId, pButton, pClick, pPlayer);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        if (isDetectorSlot(pIndex)) {
            return ItemStack.EMPTY;
        }

        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex == 0) {
            if (!moveItemStackTo(sourceStack, 1, 37, true)) {
                return ItemStack.EMPTY;
            }
        }
        else if (pIndex >= 1 && pIndex < 28) {
            if (!moveItemStackTo(sourceStack, 0, 1, false)) {
                if (!moveItemStackTo(sourceStack, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }
        else if (pIndex >= 28 && pIndex < 37) {
            if (!moveItemStackTo(sourceStack, 0, 1, false)) {
                if (!moveItemStackTo(sourceStack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }
        else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        if (isDetectorSlot(pSlot.index)) {
            return false;
        }
        return super.canTakeItemForPickAll(pStack, pSlot);
    }

    private boolean isDetectorSlot(int pSlotIndex) {
        if (blockedSlot == -1) return false;

        if (blockedSlot < 9) {
            return pSlotIndex == (28 + blockedSlot);
        } else {
            return pSlotIndex == (blockedSlot - 8);
        }
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        if (!pPlayer.level().isClientSide()) {
            EchoDetectorItem.setStoredItem(echoDetectorItem, container.getItem(0));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !echoDetectorItem.isEmpty() &&
                (pPlayer.getMainHandItem() == echoDetectorItem ||
                        pPlayer.getOffhandItem() == echoDetectorItem);
    }

    private void addPlayerInventory(Inventory pInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(pInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory pInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(pInventory, i, 8 + i * 18, 142));
        }
    }
}