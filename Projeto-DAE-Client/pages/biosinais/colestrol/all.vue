<template>
  <div class="pt-4">
    <div>
    <b-container class="modal-content rounded-6 shadow p-4 "  >
      <h1 class =" pb-4" style="text-align:center">Colestrol</h1>
      <b-table  :items="colestrol" :fields="fields" title="Colestrol" style="float:left;">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-dark btn-sm"
            :to="`/biosinais/colestrol/${row.item.id}/updateColestrol`">Atualizar</nuxt-link>
          <button
            class="btn btn-danger btn-sm" cli
            @click="apagar(row.item.id)">Apagar</button>


        </template>
      </b-table>
      <b-row >
        <b-col lg="6" v-if="doctor" class="pb-4"><nuxt-link   class="btn btn-dark btn-sm" to="/biosinais/colestrol/createColestrol"  >Inserir Registo</nuxt-link></b-col>

      </b-row>
    </b-container>
    </div>
    <div class="p-4">
      <b-container class="modal-content rounded-6 shadow" >
        <caption style="text-align:center">Gráfico</caption>
        <chartjs-line v-if="ready" v-bind:labels = "labels" v-bind:data="data"></chartjs-line>
      </b-container>
    </div>



  </div>



</template>

<script>
export default {
  data () {
    return {
      graphData: [],
      ready: false,
      labels: [],
      data: [],

      fields: [
        {
        key: 'name',
        label: 'Utente',
        },
        {
          key: 'date',
          label: 'Data',
        },
        {
          key: 'value[0]',
          label: 'Nivel de Colestrol',
        },
        {
          key: 'classification',
          label: 'Classificação',
        },
        {
          key: 'descricao',
          label: 'Descrição',
        },
        {
          key: 'actions',
          label: 'Actions',

        }
      ],
      colestrol: [],
      user: null,


    }
  },
  created () {
        this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
          .then((user) => {
            this.user = user
            this.$axios.$get('/api/biosinais/colestrol/')
              .then((colestrol) => {
                this.colestrol = colestrol
              })
              this.$axios.$get('/api/biosinais/colestrol/graph')
                                      .then((graph) => {
                                        this.labels = graph.label
                                        this.data = graph.data
                                        this.ready = true

                                      })
          })


      },

      methods: {
      apagar: function (value){

        this.$axios.$delete('/api/biosinais/colestrol/'+value)
          .then(() => {

            const indice = this.data.findIndex(colestrol => colestrol.id === value)
            if (~indice){
              this.data.splice(indice, 1)
              this.labels.splice(indice, 1)
            }
            const indece2 = this.colestrol.findIndex(colestrol => colestrol.id === value)
            if (~indece2)
              this.colestrol.splice(indece2, 1)
          })
      },

      },
     computed: {
    // a computed getter

    doctor() {
      if (this.$auth.user.groups =="Doutor"){
        return true;
      }
      else {
        return false;
      }
    },
  }
    }
</script>

<style scoped>

</style>
