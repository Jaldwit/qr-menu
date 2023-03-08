import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { DashboardTableComponent } from '../../common/dashboard-table/dashboard-table.component';
import { CategoryDashboardService } from '../category-dashboard.service';

@Component({
    selector: 'category-table',
    templateUrl: './category-table.component.html',
    styleUrls: ['./category-table.component.scss']
})
export class CategoryTableComponent extends DashboardTableComponent {

    constructor(
        public override service: CategoryDashboardService,
        public override dialog: MatDialog
    ) {
        super(service, dialog);
        this.embeddedList = 'categoryList';
    }
    
}
