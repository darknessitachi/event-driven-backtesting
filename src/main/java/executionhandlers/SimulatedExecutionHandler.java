﻿//namespace BackTesting.Model.ExecutionHandlers
//{
//    using BackTesting.Model.DataHandlers;
//    using BackTesting.Model.Events;
//    using BackTesting.Model.MarketData;
package executionhandlers;

import java.time.LocalDateTime;

import datahandlers.IDataHandler;
import events.FillEvent;
import events.IEventBus;
import events.OrderEvent;

public class SimulatedExecutionHandler implements IExecutionHandler
    {
        private  int CONST_ExecutionDelaySeconds = 5;

        private  IEventBus eventBus;
        private  IDataHandler bars;

        public SimulatedExecutionHandler(IEventBus eventBus, IDataHandler bars)
        {
            this.eventBus = eventBus;
            this.bars = bars;
        }

        public void ExecuteOrder(OrderEvent orderEvent)
        {
            // Simulate order execution delay
            LocalDateTime dateTime = orderEvent.getOrderTime();//.AddSeconds(CONST_ExecutionDelaySeconds);

            double closePrice = this.bars.GetLastClosePrice(orderEvent.getSymbol());
            double fillCost = closePrice * orderEvent.getQuantity() ;

            FillEvent fillEvent = new FillEvent(
                dateTime,
                orderEvent.getSymbol(), 
                "ARCA", 
                orderEvent.getQuantity(), 
                orderEvent.getOrderDirection(), 
                fillCost);

            this.eventBus.Put(fillEvent);
        }
    }
