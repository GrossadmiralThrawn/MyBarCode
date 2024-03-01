package com.example.mybarcode.hardwareInteraction.memoryinteraction

interface IExport {
    public fun connect(): Boolean
    public fun export(): Boolean
}