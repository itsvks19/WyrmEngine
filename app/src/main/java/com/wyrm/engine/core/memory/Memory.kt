package com.wyrm.engine.core.memory

import android.app.ActivityManager
import android.os.Debug
import com.wyrm.engine.ext.doIf
import com.wyrm.engine.ext.getCoreContext

class Memory {
  var allocatedMemory = 0f
    private set
  var nativeAllocatedMemory = 0f
    private set
  var vmHeap = 0f
    private set
  var maxMemory = 0f
    private set
  var usedMemory = 0f
    private set
  var deviceRam = 0f
    private set

  private var B_AllocatedMemory = 0L
  private var B_NativeAllocatedMemory = 0L
  private var B_VMHeap = 0L
  private var B_VMMaxMemory = 0L

  fun update() {
    val totalMemory = Runtime.getRuntime().totalMemory()
    val mMaxMemory = Runtime.getRuntime().maxMemory()
    B_VMHeap = totalMemory
    B_AllocatedMemory = totalMemory - Runtime.getRuntime().freeMemory()
    B_NativeAllocatedMemory = Debug.getNativeHeapAllocatedSize()
    B_VMMaxMemory = mMaxMemory

    val vmHeapInMB = (B_VMHeap / 1024f) / 1024f
    vmHeap = vmHeapInMB

    val allocatedMemoryInMB = (B_AllocatedMemory / 1024f) / 1024f
    allocatedMemory = allocatedMemoryInMB

    val nativeAllocatedMemoryInMB = (B_NativeAllocatedMemory / 1024f) / 1024f
    nativeAllocatedMemory = nativeAllocatedMemoryInMB

    maxMemory = (mMaxMemory / 1024f) / 1024f
    usedMemory = vmHeapInMB + allocatedMemoryInMB + nativeAllocatedMemoryInMB

    doIf(getCoreContext()) {
      val memoryInfo = ActivityManager.MemoryInfo()
      getCoreContext().getSystemService(ActivityManager::class.java).getMemoryInfo(memoryInfo)

      doIf(deviceRam == 0f) {
        deviceRam = (memoryInfo.totalMem / 1024f) / 1024f
      }
    }
  }
}