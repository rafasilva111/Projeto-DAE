<template>
  <div class="">
    <div>
      <div style="width: 100%; display: table;">
        <div v-for="outro in outrosCat" style="display: table-row">
          <div class="p-3" style="width: 600px; display: table-cell; ">
            <b-container class="modal-content rounded-4 shadow p-4" fluid="sm">
              <h1 class="pb-4" style="text-align:center">{{outro.name}}</h1>
              <b-table striped :items="qualquer(outro.name)" :fields="fields" style="float:left;">
                <template v-slot:cell(actions)="row">
                  <nuxt-link
                    class="btn btn-dark btn-sm"
                    :to="`/biosinais/pesagem/${row.item.id}/updatePesagem`">Atualizar
                  </nuxt-link>
                  <button
                    class="btn btn-danger btn-sm"
                    @click="apagar(row.item.id)">Apagar
                  </button>
                </template>
              </b-table>
              <b-row>
                <b-col lg="6" class="pb-4">
                  <nuxt-link class="btn btn-dark btn-sm" v-if="doctor" :to="`/biosinais/outros/${outro.id}/create/`">Inserir Registo
                  </nuxt-link>
                  <button  class="btn btn-danger btn-sm" v-if="!doctor" to="/biosinais/pesagem/createPesagem">Eliminar Tipo
                  </button>
                </b-col>
              </b-row>
            </b-container>
          </div>


        </div>
      </div>
      <div class="p-3">
        <b-container>
          <div >
            <b-button class="btn btn-dark btn-sm" v-if="!doctor" float @click.prevent="adicionarTipo">Adicionar novo tipo de dado Biomedico</b-button>
          </div>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      graphData: [],
      ready: false,
      labels: [],
      data: [],
      fields: [

        {
          key: 'date',
          label: 'Utente',
        },
        {
          key: 'date',
          label: 'Data',
        },
        {
          key: 'value',
          label: 'Valor medido',
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
      outrosCat: [],
      user: null,


    }
  },
  created() {

        this.$axios.$get('/api/admin/outro')
          .then((outrosCat) => {
            this.outrosCat = outrosCat
          })
        this.$axios.$get('/api/biosinais/outro')
         .then((outros) => {
        this.data = outros
      })
  },

  methods: {
    apagar: function (value) {

      this.$axios.$delete('/api/biosinais/outro/'+value)
        .then(() => {
          const indice = this.data.findIndex(pesagem => pesagem.id === value)
          if (~indice)
            this.data.splice(indice, 1)
        })
    },

    adicionarTipo(){
      this.$router.push('/admin/biosinais/create')
    },
    qualquer(value){
      return this.data.filter(function (el){

        return el.name ==value;
      });
    }

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
