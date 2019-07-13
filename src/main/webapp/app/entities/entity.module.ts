import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'orders',
        loadChildren: './orders/orders.module#Project2OrdersModule'
      },
      {
        path: 'customer',
        loadChildren: './customer/customer.module#Project2CustomerModule'
      },
      {
        path: 'collect',
        loadChildren: './collect/collect.module#Project2CollectModule'
      },
      {
        path: 'variety',
        loadChildren: './variety/variety.module#Project2VarietyModule'
      },
      {
        path: 'cinema',
        loadChildren: './cinema/cinema.module#Project2CinemaModule'
      },
      {
        path: 'round',
        loadChildren: './round/round.module#Project2RoundModule'
      },
      {
        path: 'movie',
        loadChildren: './movie/movie.module#Project2MovieModule'
      },
      {
        path: 'director',
        loadChildren: './director/director.module#Project2DirectorModule'
      },
      {
        path: 'city',
        loadChildren: './city/city.module#Project2CityModule'
      },
      {
        path: 'play',
        loadChildren: './play/play.module#Project2PlayModule'
      },
      {
        path: 'actor',
        loadChildren: './actor/actor.module#Project2ActorModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2EntityModule {}
